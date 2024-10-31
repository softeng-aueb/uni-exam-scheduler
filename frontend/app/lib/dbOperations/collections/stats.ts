"use server";

import { connectToDatabase } from "../_mongoClientUI";
import { dbFailureRes, DbOperationsRes, dbSuccessRes } from "../types/mongoResTypes";

export async function getConfigStats(collectionName: string, query: object, findOne: boolean = false): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    let res;

    if (findOne) {
      res = await db.collection(collectionName).findOne(query);
      res = res?.msisdns?.length || 0;
    } else {
      res = await db.collection(collectionName).countDocuments(query);
    }

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
