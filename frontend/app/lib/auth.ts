"use server";

import bcrypt from "bcrypt";

export async function hashPassword(password: any) {
  const salt = await bcrypt.genSalt(10);
  return await bcrypt.hash(password, salt);
}

// Compare the password of an already fetched user (using `findUserByUsername`) and compare the
// password for a potential match
export async function validatePassword(user: any, inputPassword: string) {
  return bcrypt.compareSync(inputPassword, user.hashed_pass);
}
