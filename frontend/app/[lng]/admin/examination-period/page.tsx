import React from "react";
import { Metadata } from "next";

import { readAcademicYears } from "@/app/lib/dbOperations";
import ExaminationPeriodComponent from "./ExaminationPeriod";

export const metadata: Metadata = {
  title: "Academic Year",
  description: "Academic Year Config",
};

async function fetchData() {
  const academicYears = await readAcademicYears();

  return academicYears;
}

export default async function ExaminationPeriodPage({ params}: any) {
  const academicYears = await fetchData();
  const {lng} = await params;
  return (
    <ExaminationPeriodComponent lng={lng} pageData={academicYears}/>
  );
}
