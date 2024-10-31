"use server";

import fs from "fs";
import path from "path";
import { connectToDatabase } from "../_mongoClientUI";
import { DbOperationsRes, dbFailureRes, dbSuccessRes } from "../types/mongoResTypes";

const collection = "Uploads";

export async function uploadFile(
  formData: FormData,
): Promise<any> {
  try {
    const {db} = await connectToDatabase();
    const uploadDir = process.env.PATH_UPLOADS || './uploads';

    // Ensure the directory exists
    if (!fs.existsSync(uploadDir)) {
      fs.mkdirSync(uploadDir, { recursive: true });
    }

    const formDataEntryValues = Array.from(formData.values());
    console.log("=========== :", formDataEntryValues);

    for (const formDataEntryValue of formDataEntryValues) {
      if (typeof formDataEntryValue === "object" && "arrayBuffer" in formDataEntryValue) {
        const file = formDataEntryValue as any;

        const buffer = Buffer.from(await file.arrayBuffer());

        const filePath = path.join(uploadDir, file.name);

        fs.writeFileSync(filePath, buffer);

       const fileDocument = {
          name: file.name,
          path: filePath,
          size: file.size,
          type: file.type,
          lastModified: file.lastModified,
          uploadedAt: new Date(),
        };
        const res = await db.collection(collection).insertOne(fileDocument);
        console.log(`File uploaded successfully to ${filePath}`);
        return dbSuccessRes(res);

      }
    }
  } catch (e: any) {
    console.error("Error uploading file:", e);
   return { error: e.message };
  }
}

export async function readUploadedFiles(): Promise<DbOperationsRes> {
  try {
    const { db } = await connectToDatabase();

    const res = await db.collection(collection).find({}).toArray();

    return dbSuccessRes(res);
  } catch (e: any) {
    return dbFailureRes(null, e);
  }
}
