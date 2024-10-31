"use client";

import React, { useEffect, useState } from "react";

// MUI
import { Container, Grid } from "@mui/material";

// Components
import Breadcrumb from "@/app/ui/Breadcrumb";
import DashboardCard from "@/app/ui/DashboardCard/DashboardCard";
import FolderIcon from "@/app/ui/Icons/Folder";
import Layout from "@/app/ui/layout";
import { getConfigStats } from "../../lib/dbOperations/collections/stats";

// helper

export default function AdminComponent() {
  const [countries, setCountries] = useState<any>("");
  const [languages, setLanguages] = useState<any>("");
  const [listings, setListings] = useState<any>("");
  const [listingCategories, setListingCategories] = useState<any>("");
  const [roles, setRoles] = useState<any>("");
  const [users, setUsers] = useState<any>("");

  // Breadcrumb data
  const breadcrumbList = [
    {
      label: "Home",
      url: "/",
    },
    {
      label: "Admin",
      url: "/admin",
    },
  ];

  const links = [
    {
      title: "Countries",
      subtitle: "Manage Countries",
      count: countries,
      url: "/admin/countries",
    },
    {
      title: "Languages",
      subtitle: "Manage Languages",
      count: languages,
      url: "/admin/languages",
    },
    {
      title: "UserRoles",
      subtitle: "Manage UserRoles",
      count: roles,
      url: "/admin/user-roles",
    },
    {
      title: "Users",
      subtitle: "Manage Users and their UserRoles",
      count: users,
      url: "/admin/users",
    },
    {
      title: "ListingCategories",
      subtitle: "Manage ListingCategories",
      count: listingCategories,
      url: "/admin/listing-categories",
    },
    {
      title: "Listings",
      subtitle: "Manage Listings",
      count: listings,
      url: "/admin/listings",
    },
  ];

  useEffect(() => {
    (async () => {
      const [
        { data: countries },
        { data: languages },
        { data: listingCategories },
        { data: listings },
        { data: roles },
        { data: users },
      ] = await Promise.all([
        getConfigStats("Countries", {}),
        getConfigStats("Languages", {}),
        getConfigStats("ListingCategories", {}),
        getConfigStats("Listings", {}),
        getConfigStats("UserRoles", {}),
        getConfigStats("Users", {}),
      ]);

      setCountries(countries);
      setLanguages(languages);
      setListingCategories(listingCategories);
      setListings(listings);
      setRoles(roles);
      setUsers(users);
    })();
  }, [countries]);
  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <Grid container spacing={2}>
          {links.map((link: any, index: number) => (
            <Grid key={index} item lg={4} md={6} xs={12}>
              <DashboardCard
                icon={<FolderIcon/>}
                title={link.title}
                titleLink={link.url}
                subtitle={link?.count}
                figures={[{ "key": link.subtitle, "val": null }]}
              />
            </Grid>
          ))}
        </Grid>
      </Container>
    </Layout>
  );
}
