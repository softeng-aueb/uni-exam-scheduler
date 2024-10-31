type SessionObj = {
  "user": any,
  "expires": Date,
  "username": string,
  "name": string,
  "email": string
}

export type MongoUiUserRefType = {
  username: string,
  email: string,
  fullname: string
};

export const getUserRefFromSession = (session: SessionObj | any)
  : MongoUiUserRefType => {
  return {
    username: session?.username ?? "",
    email: session?.email ?? "",
    fullname: session?.name ?? "",
  };
};
