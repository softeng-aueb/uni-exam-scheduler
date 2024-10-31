import { getServerSession, type NextAuthOptions } from "next-auth";
import Credentials from "next-auth/providers/credentials";
import GoogleProvider from "next-auth/providers/google";
import { userService } from "./services/useService";
import { saveGoogleAuthUserInDb } from "./services/authService";
import { getUsersWithRoles } from "../app/lib/dbOperations";

export const authOptions: NextAuthOptions = {
  session: {
    strategy: "jwt",
  },
  callbacks: {
    // here add the signIn
    async signIn({ user, account, email, credentials }) {
      console.log(`${new Date().toISOString()} > signIn > user: ${JSON.stringify(user)}`);
      console.log(`${new Date().toISOString()} > signIn > credentials: ${JSON.stringify(credentials)}`);

      if (!credentials) {
        await saveGoogleAuthUserInDb(user);
        // return true;
      }

      return true;
    },
    async jwt({ token, user, account }) {
      // If the user logged in with OAuth. We have to fetch again
      let _user: any = user;

      if (account?.type === "oauth") {
        const query = {
          $or: [{ username: user?.email }, { email: user?.email }],
        };
        const { data: userData } = await getUsersWithRoles(query);
        _user = userData;
      }

      if (_user?.username) {
        token["username"] = _user.username;
      }
      if (_user?.email) {
        token["email"] = _user.email;
      }
      if (_user?.is_admin) {
        token["is_admin"] = _user.is_admin;
      }
      if (_user?.roles) {
        token["roles"] = _user.roles;
      }
      if (account?.accessToken) {
        token.accessToken = account.accessToken;
      }
      return token;
    },
    async session({ session, token }: { session: any; token: any }) {
      session.username = token.username;
      session.name = token.name;
      session.email = token.email;
      session.is_admin = token.is_admin;
      session.roles = token.roles;
      return session;
    },
  },
  pages: {
    signIn: "/login",
  },
  secret: process.env.AUTH_SECRET,
  providers: [
    Credentials({
      name: "Credentials",
      credentials: {
        email: { label: "Email", type: "text", placeholder: "email" },
        password: { label: "Password", type: "password" },
      },
      async authorize(credentials, req) {
        return userService.authenticate(credentials);
      },
    }),
    GoogleProvider({
      clientId: process.env.AUTH_GOOGLE_ID as string,
      clientSecret: process.env.AUTH_GOOGLE_SECRET as string,
      authorization: {
        params: {
          prompt: "consent",
          access_type: "offline",
          response_type: "code",
        },
      },
    }),
  ],
};

export const getServerAuthSession = () => getServerSession(authOptions);
