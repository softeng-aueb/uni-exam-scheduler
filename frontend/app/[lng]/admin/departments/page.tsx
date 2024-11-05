import React from "react";
import { Metadata } from "next";
import DepartmentsComponent from "./DepartmentsComponent";
import { readDepartments } from "@/app/lib/dbOperations";

export const metadata: Metadata = {
  title: "Departments",
  description: "Departments",
};

async function fetchData() {
  const departments = await readDepartments();
  return departments;
}

export default async function DepartmentsPage({ params: { lng } }: any) {
  const departments = await fetchData();
  return (
    <DepartmentsComponent lng={lng} pageData={departments}/>
  );
}
