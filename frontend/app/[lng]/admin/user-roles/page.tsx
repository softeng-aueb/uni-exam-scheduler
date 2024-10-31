import React from "react";
import { Metadata } from "next";
import RolesComponent from "./RolesComponent";

export const metadata: Metadata = {
  title: "UserRoles",
  description: "UserRoles",
};

export default function RolesPage({ params: { lng } }: any) {
  return (
    <RolesComponent lng={lng}/>
  );
}
