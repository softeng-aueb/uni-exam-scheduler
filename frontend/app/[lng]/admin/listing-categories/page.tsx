import React from "react";
import { Metadata } from "next";
import ListingCategoriesComponent from "./ListingCategoriesComponent";

export const metadata: Metadata = {
  title: "Listing Categories",
  description: "Listing Categories",
};

export default function ListingCategoriesPage({ params: { lng } }: any) {
  return (
    <ListingCategoriesComponent lng={lng}/>
  );
}
