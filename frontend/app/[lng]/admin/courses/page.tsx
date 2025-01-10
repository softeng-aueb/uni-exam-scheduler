import React from "react";
import { Metadata } from "next";
import CoursesComponent from "./CoursesComponent";
import { readCourses } from "@/app/lib/dbOperations";

export const metadata: Metadata = {
  title: "Courses",
  description: "courses",
};

async function fetchData() {
  const courses = await readCourses();
  return courses;
}

export default async function CoursesPage({ params: { lng } }: any) {
  const courses = await fetchData();

  return (
    <CoursesComponent lng={lng} pageData={courses}/>
  );
}
