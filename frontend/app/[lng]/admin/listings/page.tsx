import React from "react";
import { Metadata } from "next";
import ListingsComponent from "./ListingsComponent";

export const metadata: Metadata = {
  title: "Listings",
  description: "Listings",
};

export default function ListingsPage({ params: { lng } }: any) {
  return (
    <ListingsComponent lng={lng}/>
  );
}
