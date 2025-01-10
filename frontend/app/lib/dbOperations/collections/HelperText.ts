"use server";

import { dbFailureRes, DbOperationsRes, dbSuccessRes } from "../types/mongoResTypes";
import { connectToDatabase } from "../_mongoClientUI";

const collection = "HelpPages";

export async function fetchPageData(pagePath: string, language: string): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).findOne({ page_path: pagePath, page_language: language });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function addPageData(data?: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).insertOne(data);

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function editPageData(data?: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const { pagePath, dataToUpdate } = data || {};

    const res = await db.collection(collection).updateOne({ page_path: pagePath }, { $set: dataToUpdate });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
