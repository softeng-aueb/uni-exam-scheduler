"use server";

import { ObjectId } from "mongodb";
import { dbFailureRes, DbOperationsRes, dbSuccessRes } from "../types/mongoResTypes";

import { connectToDatabase } from "../_mongoClientUI";

import data from "../../../../[more]/examinatioPeriodId.json"

export async function fetchCalendarEvents(query?: any): Promise<DbOperationsRes> {
  try {
    return dbSuccessRes(data);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function updateCalendarEvent(_id: string, updateObj: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection("CalendarEvents").updateOne({ _id: new ObjectId(_id) }, { $set: updateObj });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
