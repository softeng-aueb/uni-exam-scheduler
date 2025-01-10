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

// DB Operations
import { deleteCourse as deleteDoc, updateCourse as updateDoc, } from "@/app/lib/dbOperations";

// Helpers
import { useTranslation } from "@/app/i18n/client";
import CreateEditModalAcademicYear from "@/app/ui/CRUDCreateEditModals/AcademicYear";
import AcademicYearsCol from "@/app/ui/CRUDColumns/Admin/AcademicYears/Cols";

const entityNamePlural = "Courses";
const entityNameSingular = "Course";
const searchSpinnerArea = "search-courses-area";
const spinnerArea = "read-courses";

export default function AcademicYearComponent({ lng, pageData }: any) {

  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);

  useEffect(() => {
    setData(pageData);
  }, []);

  const breadcrumbList = [{
    label: "Home",
    url: "/",
  }, {
    label: "Admin",
    url: "/admin",
  }, {
    label: t("academic.label_many"),
    url: "/admin/academic-year",
  }];

  const handleModalClose = () => setAddEditModalOpen(false);

  const handleModalOpen = () => setAddEditModalOpen(true);

  const onModalOpen = () => {
    setAddEditModalOpen(true);
  };

  const columnsPayload = {
    data,
    deleteDoc,
    entityNameSingular,
    handleModalOpen,
    setData,
    updateDoc,
  };
  const columns = AcademicYearsCol(columnsPayload, t);

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <PageCardHeader
          onClick={onModalOpen}
          searchProps={false}
          showCreateModal={true}
          title={t("academic.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalAcademicYear
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        lng={lng}
      />
    </Layout>
  );
}
