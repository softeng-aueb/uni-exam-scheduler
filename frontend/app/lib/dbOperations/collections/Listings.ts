// >>> TEMPLATE FOR CRUD
"use server";

import { ObjectId } from "mongodb";
import { connectToDatabase } from "../_mongoClientUI";
import { dbFailureRes, DbOperationsRes, dbSuccessRes } from "../types/mongoResTypes";

const collection = "Listings";

export async function createListing(data: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).insertOne(data);

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function readListing(_id: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).findOne({ _id: new ObjectId(_id) });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function readListings(): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    // const res = await db.collection(collection).find({}).sort({ name: 1 }).toArray();

    const res = await db.collection(collection).aggregate([
      // Convert listing_category_id to ObjectId
      {
        $addFields: {
          listing_category_id: { "$toObjectId": "$listing_category_id" },
        },
      },
      // Lookup ListingCategories
      {
        $lookup: {
          from: "ListingCategories",
          localField: "listing_category_id",
          foreignField: "_id",
          as: "listingCategory",
        },
      },
      // Keep related name
      {
        $addFields: {
          listing_category_name: { "$arrayElemAt": ["$listingCategory.name", 0] },
        },
      },
      // Remove lookup array
      {
        $project: {
          listingCategory: 0,
        },
      },

    ]).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function readListsActive(): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).find({ status: "ACTIVE" }).sort({ name: 1 }).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function deleteListing(_id: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const deleted_cat = await db.collection(collection).findOneAndDelete({ _id: new ObjectId(_id) });

    return dbSuccessRes(deleted_cat);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function updateListing(_id: string, updateObj: any): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    delete updateObj._id;

    const res = await db.collection(collection).updateOne({ _id: new ObjectId(_id) }, { $set: updateObj });

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}

export async function searchListing(searchText: string): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    db.collection(collection).createIndex({
      name: "text",
    });

    const res = await db.collection(collection).find({ $text: { $search: searchText } }).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
