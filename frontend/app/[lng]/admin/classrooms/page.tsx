import React from "react";
import { Metadata } from "next";
import ClassroomsComponent from "./ClassroomsComponent";
import { readClassrooms } from "@/app/lib/dbOperations";

export const metadata: Metadata = {
  title: "Classrooms",
  description: "Classrooms",
};

async function fetchData() {
  const classrooms = await readClassrooms();
  return classrooms;
}

export default async function ClassroomsPage({ params: { lng } }: any) {
  const classrooms = await fetchData();
  return (
    <ClassroomsComponent lng={lng} pageData={classrooms}/>
  );
}
