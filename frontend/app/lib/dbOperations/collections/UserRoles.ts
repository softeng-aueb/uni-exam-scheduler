// >>> TEMPLATE FOR CRUD
"use server";

import { ObjectId } from "mongodb";
import { connectToDatabase } from "../_mongoClientUI";
import { dbFailureRes, DbOperationsRes, dbSuccessRes } from "../types/mongoResTypes";

const collection = "UserRoles";

export async function createRole(data: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).insertOne(data);

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function readRoles(): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).find({}).sort({ permissions: 1, name: 1 }).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function deleteRole(_id: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const deleted_cat = await db.collection(collection).findOneAndDelete({ _id: new ObjectId(_id) });

    return dbSuccessRes(deleted_cat);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function updateRole(_id: string, updateObj: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    delete updateObj._id;

    const res = await db.collection(collection).updateOne({ _id: new ObjectId(_id) }, { $set: updateObj });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function searchRoles(searchText: string): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    db.collection(collection).createIndex({
      name: 1,
    });

    // TODO: (this is regex based search) After confirmation, either go back to full text search or keep it as it is
    const regex = new RegExp(searchText, "i");

    const res = await db.collection(collection).find({
      $or: [
        { name: { $regex: regex } },
      ],
    }).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
