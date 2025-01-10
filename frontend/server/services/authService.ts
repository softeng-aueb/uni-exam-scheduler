"use server";

// import { setCookie } from "cookies-next";

import { connectToDatabase } from "@/app/lib/dbOperations/_mongoClientUI";
import { createUser, getUsersWithRoles } from "@/app/lib/dbOperations";

export async function getUser(query: any): Promise<any> {
  try {
    const { db } = await connectToDatabase();

    let user = await db.collection("Users").aggregate([{
      $match: query,
    }, {
      $lookup: {
        from: "Roles",
        localField: "roles",
        foreignField: "name",
        as: "UserRoles",
      },
    }]).toArray();

    user = user[0];
    if (!user) {
      return null;
    }

    return user;
  } catch (error: any) {
    console.error("Failed to fetch user:", error);
    throw new Error("Failed to fetch user.");
  }
}

export const saveGoogleAuthUserInDb = async (authUser: any) => {
  // console.log(`${new Date().toISOString()} > saveGoogleAuthUserInDb > authUser: ${JSON.stringify(authUser)}`);

  const id = authUser.id;
  const name = authUser.name;
  const email = authUser.email;
  const image = authUser.image;
  const username = email?.split("@").shift();

  // Check if same user already exists
  const query = {
    $or: [{ username }, { email }],
  };

  const { data: user } = await getUsersWithRoles(query);

  // console.log(`${new Date().toISOString()} > saveGoogleAuthUserInDb > user: ${JSON.stringify(user)}`);

  // Set project in cookies
  // setCookie("currentProject", user?.projects[0]);

  if (!user) {
    await createUser({
      id,
      name,
      email,
      image,
      username,
      is_admin: false,
      roles: [],
    });
  }
};
