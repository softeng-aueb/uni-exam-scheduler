import { setCookie } from "cookies-next";

import { validatePassword } from "@/app/lib/auth";
import { getUser } from "./authService";

export const userService = {
  authenticate,
};

async function authenticate(credentials) {
  // const parsedCredentials = z
  //    .object({ email: z.string().email(), password: z.string().min(6) })
  //    .safeParse(credentials);

  if (credentials) {
    const { email, password } = credentials;
    const query = { $or: [{ email }, { username: email }] };
    const user = await getUser(query);

    if (!user) return null;
    const passwordMatch = await validatePassword(user, password);
    if (passwordMatch) {
      setCookie("currentProject", user?.projects[0]);

      return user;
    }
  }
  console.log("Invalid credentials");
  return null;
}
