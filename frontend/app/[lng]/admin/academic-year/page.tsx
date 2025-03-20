import React from "react";
import { Metadata } from "next";
import AcademicYearComponent from "./AcademicYear";
import { readAcademicYears } from "@/app/lib/dbOperations";

export const metadata: Metadata = {
  title: "Academic Year",
  description: "Academic Year Config",
};

async function fetchData() {
  const academicYears = await readAcademicYears();

  return academicYears;
}

export default async function AcademicYearPage({ params}: any) {
  const academicYears = await fetchData();
  const {lng} = await params;
  return (
    <AcademicYearComponent lng={lng} pageData={academicYears}/>
  );
}
