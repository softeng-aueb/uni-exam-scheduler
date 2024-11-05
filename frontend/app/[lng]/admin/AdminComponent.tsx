"use client";

import React from "react";

// MUI
import { Container, Grid } from "@mui/material";

// Components
import Breadcrumb from "@/app/ui/Breadcrumb";
import DashboardCard from "@/app/ui/DashboardCard/DashboardCard";
import FolderIcon from "@/app/ui/Icons/Folder";
import Layout from "@/app/ui/layout";

// helper

export default function AdminComponent() {

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
      title: "Courses",
      subtitle: "Manage Courses",
      url: "/admin/courses",
    },
    {
      title: "Departments",
      subtitle: "Manage Departments",
      url: "/admin/departments",
    },
    {
      title: "Supervisors",
      subtitle: "Manage Supervisors",
      url: "/admin/supervisors",
    },
    {
      title: "Classrooms",
      subtitle: "Manage Classrooms",
      url: "/admin/classrooms",
    },
    {
      title: "Academic Year",
      subtitle: "Manage Academic Year",
      url: "/admin/academic-year",
    },
    {
      title: "Examination Period",
      subtitle: "Manage Examination Period",
      url: "/admin/examination-period",
    },

  ];

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
                figures={[{ "key": link.subtitle, "val": null }]}
              />
            </Grid>
          ))}
        </Grid>
      </Container>
    </Layout>
  );
}
