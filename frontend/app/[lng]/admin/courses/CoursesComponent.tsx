"use client";

import React, { useEffect, useState } from "react";

// MUI
import { Container } from "@mui/material";

// Components
import Breadcrumb from "@/app/ui/Breadcrumb";
import Layout from "@/app/ui/layout";
import PageCardHeader from "@/app/ui/PageCardHeader";
import SpinnerWithPromise from "@/app/ui/Spinner/SpinnerPromise";
import { DataGridComponent } from "@/app/ui/DataGrid";
// Components
import CreateEditModalConfigCourses from "@/app/ui/CRUDCreateEditModals/Courses";
import CourseCol from "@/app/ui/CRUDColumns/Admin/Courses/Cols";

// DB Operations
import { deleteCourse as deleteDoc, updateCourse as updateDoc, } from "@/app/lib/dbOperations";

// Helpers
import { useTranslation } from "@/app/i18n/client";

const entityNamePlural = "Courses";
const entityNameSingular = "Course";
const searchSpinnerArea = "search-courses-area";
const spinnerArea = "read-courses";

export default function CoursesComponent({ lng, pageData }: any) {

  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);
  const [dataAll, setDataAll] = useState<any>([]);
  const [search, setSearch] = useState<string>("");
  const [selectedDoc, setSelectedDoc] = useState<any>({});

  useEffect(() => {
    async function init() {

      setDataAll(pageData);
      setData(pageData);
    }

    (async () => {
      await init();
    })();
  }, []);

  const breadcrumbList = [{
    label: "Home",
    url: "/",
  }, {
    label: "Admin",
    url: "/admin",
  }, {
    label: t("courses.label_many"),
    url: "/admin/courses",
  }];

  const handleModalClose = () => setAddEditModalOpen(false);

  const handleModalOpen = () => setAddEditModalOpen(true);

  const onModalOpen = () => {
    setSelectedDoc({});
    setAddEditModalOpen(true);
  };

  const columnsPayload = {
    data,
    deleteDoc,
    entityNameSingular,
    handleModalOpen,
    setData,
    setSelectedDoc,
    updateDoc,
  };
  const columns = CourseCol(columnsPayload, t);

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <PageCardHeader
          onClick={onModalOpen}
          searchProps={false}
          showCreateModal={true}
          title={t("courses.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalConfigCourses
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        course={selectedDoc}
        lng={lng}
      />
    </Layout>
  );
}
