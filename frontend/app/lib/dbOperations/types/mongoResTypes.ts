import { ObjectId } from "mongodb";

export interface MongoUpdateOneResType {
  acknowledged: boolean;
  modifiedCount: number;
  upsertedId: null | ObjectId;
  upsertedCount: number;
  matchedCount: number;
  hasError?: boolean;
}

export interface DBOperationsInsertOneResType {
  data: MongoInsertOneResType | null | undefined,
  error?: Error | string,
}

export interface MongoUpdateManyResType {
  acknowledged: boolean;
  modifiedCount: number;
  upsertedId: null | ObjectId;
  upsertedCount: number;
  matchedCount: number;
  hasError?: boolean;
}

export interface MongoInsertOneResType {
  acknowledged: boolean;
  insertedId: typeof ObjectId;
  hasError?: boolean;
}

export interface DBOperationsInsertOneResType {
  data: MongoInsertOneResType | null | undefined,
  error?: Error | string,
}

export interface MongoExceptionResType {
  hasError: boolean;
  msg?: any;
}

export type DbOperationsDataRes =
  | MongoInsertOneResType
  | MongoUpdateManyResType
  | MongoExceptionResType
  | MongoUpdateOneResType
  | DBOperationsInsertOneResType;

export interface DbOperationsRes {
  data: DbOperationsDataRes | null | undefined; // + Rest Interfaces
  error?: Error | string;
}

export interface DbOperationsInsertRes {
  data: MongoInsertOneResType | null | undefined; // + Rest Interfaces
  error?: Error | string;
}

export function dbSuccessRes(data: DbOperationsDataRes | MongoInsertOneResType | null | undefined | any): DbOperationsRes | DbOperationsInsertRes {
  // return { data };
  return { data: JSON.parse(JSON.stringify(data)) };
}

export function dbFailureRes(data: DbOperationsDataRes | null | undefined | any, error: Error | string): DbOperationsRes | DbOperationsInsertRes {
  try {
    // Handling/fixing object
    return {
      data: JSON.parse(JSON.stringify(data)),
      error,
    };
  } catch (e: any) {
    return { data: e, error };
  }
}

export type DbReadResType = {
  data: null | any,
  error?: string | Error
};

export function dbOperationsRes(data: null, error?: Error | string): DbReadResType
export function dbOperationsRes(data: MongoInsertOneResType, error?: Error | string): DBOperationsInsertOneResType
export function dbOperationsRes(data: DbOperationsDataRes | null | undefined, error?: Error | string): DbOperationsRes | DbOperationsInsertRes | DbReadResType {
  if (error) return { data, error };
  return { data: JSON.parse(JSON.stringify(data)) };
}

export interface DbOperationsCountResInterface {
  data: null | number,
  error?: string | Error
}

export function dbOperationsCountRes(data: number, error?: string | Error): DbOperationsCountResInterface {
  if (error) return { data, error };
  return { data };
}
