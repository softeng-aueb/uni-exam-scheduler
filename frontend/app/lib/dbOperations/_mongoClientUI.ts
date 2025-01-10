import * as mongoDB from "mongodb";

const MONGODB_URI: string = process.env.MONGODB_UI_URI!;

// check the MongoDB URI
if (!MONGODB_URI) {
  throw new Error("Define the MONGODB_URI environmental variable");
}

let cachedClient: any = null;
let cachedDb: any = null;

export async function connectToDatabase() {
  // check the cached.
  if (cachedClient && cachedDb) {
    // load from cache
    return {
      client: cachedClient,
      db: cachedDb,
    };
  }

  // set the connection options
  const options = {
    // useNewUrlParser: true,
    // useUnifiedTopology: true,
  };

  // Connect to cluster
  const client = new mongoDB.MongoClient(
    MONGODB_URI,
    options as mongoDB.MongoClientOptions,
  );
  await client.connect();
  const db = client.db();

  // set cache
  cachedClient = client;
  cachedDb = db;

  return {
    client: cachedClient,
    db: cachedDb,
  };
}
