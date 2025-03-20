import { validatePassword } from "@/app/lib/auth";
import { getUser } from "./authService";

export const userService = {
  authenticate,
};

async function authenticate(credentials) {
  // const parsedCredentials = z
  //    .object({ email: z.string().email(), password: z.string().min(6) })
  //    .safeParse(credentials);


  console.log(`Authenticating with credentials: ${credentials.email}`)

  if (credentials) {
    const { email, password } = credentials;
    const query = { $or: [{ email }, { username: email }] };
    const user = await getUser(query);

    console.log(`Retrieved user: ${user.name}`)
    
    if (!user) return null;

    user.hashed_pass = user.password

    const passwordMatch = await validatePassword(user, password);
    
    if (passwordMatch) {
      return user;
    }
  }
  console.log("Invalid credentials");
  return null;
}
