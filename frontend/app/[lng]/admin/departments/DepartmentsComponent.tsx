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
import CreateEditModalConfigDepartments from "@/app/ui/CRUDCreateEditModals/Departments";
import DepartmentsCol from "@/app/ui/CRUDColumns/Admin/Departments/Cols";

// Helpers
import { useTranslation } from "@/app/i18n/client";


const entityNameSingular = "Department";

const spinnerArea = "read-departments";

export default function DepartmentsComponent({ lng, pageData }: any) {

  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);
  const [dataAll, setDataAll] = useState<any>([]);
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
    label: t("departments.label_many"),
    url: "/admin/departments",
  }];

  const handleModalClose = () => setAddEditModalOpen(false);
  const handleModalOpen = () => setAddEditModalOpen(true);

  const onModalOpen = () => {
    setSelectedDoc({});
    setAddEditModalOpen(true);
  };


  const columnsPayload = {
    data,
    entityNameSingular,
    handleModalOpen,
    setData,
    setSelectedDoc,
  };
  const columns = DepartmentsCol(columnsPayload, t);

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <PageCardHeader
          onClick={onModalOpen}
          searchProps={false}
          showCreateModal={false}
          title={t("departments.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalConfigDepartments
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        department={selectedDoc}
        lng={lng}
      />
    </Layout>
  );
}
