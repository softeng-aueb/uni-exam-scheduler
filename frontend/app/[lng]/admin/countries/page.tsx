import React from "react";
import { Metadata } from "next";
import CountriesComponent from "./CountriesComponent";

export const metadata: Metadata = {
  title: "Countries",
  description: "countries",
};

export default function CountriesPage({ params: { lng } }: any) {
  return (
    <CountriesComponent lng={lng}/>
  );
}
