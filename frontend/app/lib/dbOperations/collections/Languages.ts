// >>> TEMPLATE FOR CRUD
"use server";

import { ObjectId } from "mongodb";
import { connectToDatabase } from "../_mongoClientUI";
import { dbFailureRes, DbOperationsRes, dbSuccessRes } from "../types/mongoResTypes";

const collection = "Languages";

export async function createLanguage(data: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).insertOne(data);

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function readLanguage(_id: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).findOne({ _id: new ObjectId(_id) });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function readLanguages(): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).find({}).sort({ name: 1 }).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function readLanguagesActive(): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).find({ status: "ACTIVE" }).sort({ name: 1 }).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function deleteLanguage(_id: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const deleted_cat = await db.collection(collection).findOneAndDelete({ _id: new ObjectId(_id) });

    return dbSuccessRes(deleted_cat);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function updateLanguage(_id: string, updateObj: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    delete updateObj._id;

    const res = await db.collection(collection).updateOne({ _id: new ObjectId(_id) }, { $set: updateObj });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function searchLanguages(searchText: string): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    db.collection(collection).createIndex({
      name: "text",
      code: "text",
      nativeName: "text",
    });

    const res = await db.collection(collection).find({ $text: { $search: searchText } }).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
