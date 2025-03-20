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

export default async function DepartmentsPage({ params}: any) {
  const departments = await fetchData();
  const {lng} = await params;
  
  return (
    <DepartmentsComponent lng={lng} pageData={departments}/>
  );
}
