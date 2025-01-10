"use server";

import { ObjectId } from "mongodb";
import { dbFailureRes, DbOperationsRes, dbSuccessRes } from "../types/mongoResTypes";

import { connectToDatabase } from "../_mongoClientUI";
import { hashPassword } from "../../auth";

const collection = "Users";

/* Users */
export async function fetchUsers(): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const data = await db.collection(collection).aggregate([
      {
        $addFields: {
          id: "$_id",
        },
      },
      {
        $sort: {
          is_admin: -1,
          email: 1,
        },
      },
    ]).toArray();

    return dbSuccessRes(data);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function searchUsers(searchText: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const searchTerm = new RegExp(searchText);

    const data = await db.collection(collection).aggregate([
      {
        $match: {
          $or: [
            { email: { $regex: searchTerm } },
            { username: { $regex: searchTerm } },
          ],
        },
      },
      {
        $addFields: {
          id: "$_id",
        },
      },
    ]).toArray();

    return dbSuccessRes(data);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function getUsersWithRoles(query: any): Promise<any> {
  // console.log(`${new Date().toISOString()} > getUsersWithRoles > query: ${JSON.stringify(query)}`);

  const { db } = await connectToDatabase();

  const users = await db
    .collection("Users")
    .aggregate([{
      $match: query,
    }, {
      $lookup: {
        from: "UserRoles",
        localField: "roles",
        foreignField: "name",
        as: "UserRoles",
      },
    }])
    .toArray();

  const user = users.length > 0 ? users?.[0] : null;

  // console.log(`${new Date().toISOString()} > getUsersWithRoles > user: ${JSON.stringify(user)}`);

  return dbSuccessRes(user);
}

export async function createUser(userObj: any): Promise<DbOperationsRes> {
  const { db } = await connectToDatabase();

  // Check if user already exists
  const dbUser = await db.collection("Users").findOne({ $or: [{ email: userObj.email }, { username: userObj.username }] });

  if (dbUser) {
    return dbFailureRes(null, "User already exists");
  }

  if (userObj?.password) {
    // Create hashed password
    userObj.hashed_pass = await hashPassword(userObj.password);

    // Delete form's passwords
    delete userObj.password;
    delete userObj.confirm_password;
  }

  console.log(`${new Date().toISOString()} > createUser > userObj: ${JSON.stringify(userObj)}`);

  const data = await db.collection("Users").insertOne(userObj);

  console.log(`${new Date().toISOString()} > createUser > data: ${JSON.stringify(data)}`);

  return dbSuccessRes(data);
}

export async function getUser(userId: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const data = await db.collection("Users").findOne({ _id: new ObjectId(userId) });

    return dbSuccessRes(data);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function getUserByEmail(email: string): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection("Users").findOne({ email });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function editUser(userId: any, userData: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    if (userData.password) {
      const hashed_pass = await hashPassword(userData.password);
      delete userData.password;
      userData["hashed_pass"] = hashed_pass;
    }

    const data = await db.collection("Users").findOneAndUpdate(
      { _id: new ObjectId(userId) },
      { $set: userData },
      { returnNewDocument: true },
    );

    return dbSuccessRes(data);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function deleteUser(userId: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const data = await db.collection("Users").findOneAndDelete({ _id: new ObjectId(userId) });

    return dbSuccessRes(data);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
