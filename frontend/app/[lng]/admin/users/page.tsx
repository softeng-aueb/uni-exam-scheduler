import React from "react";
import { Metadata } from "next";
import UsersComponent from "./UsersComponent";

export const metadata: Metadata = {
  title: "Users",
  description: "Users",
};

export default function UsersPage({ params: { lng } }: any) {
  return (
    <UsersComponent lng={lng}/>
  );
}
