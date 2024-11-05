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
import CreateEditModalSupervisors from "../../../ui/CRUDCreateEditModals/Supervisors";
import SupervisorsCol from "@/app/ui/CRUDColumns/Admin/Supervisors/Cols";

// DB Operations
import { deleteSupervisor as deleteDoc, updateSupervisor as updateDoc, } from "@/app/lib/dbOperations";

// Helpers
import { useTranslation } from "@/app/i18n/client";

const entityNamePlural = "Supervisors";
const entityNameSingular = "Supervisor";
const searchSpinnerArea = "search-supervisors-area";
const spinnerArea = "read-supervisors";

export default function SupervisorsComponent({ lng, pageData }: any) {
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
    label: t("supervisors.label_many"),
    url: "/admin/supervisors",
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
  const columns = SupervisorsCol(columnsPayload, t);

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <PageCardHeader
          onClick={onModalOpen}
          searchProps={false}
          showCreateModal={true}
          title={t("supervisors.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalSupervisors
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        supervisor={selectedDoc}
        lng={lng}
      />
    </Layout>
  );
}
