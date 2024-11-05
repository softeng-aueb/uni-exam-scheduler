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
import CreateEditModalClassrooms from "../../../ui/CRUDCreateEditModals/Classrooms";
import ClassroomsCol from "@/app/ui/CRUDColumns/Admin/Classrooms/Cols";

// DB Operations
import { deleteClassroom as deleteDoc, updateClassroom as updateDoc, } from "@/app/lib/dbOperations";

// Helpers
import { useTranslation } from "@/app/i18n/client";

const entityNamePlural = "Classrooms";
const entityNameSingular = "Classroom";
const searchSpinnerArea = "search-classrooms-area";
const spinnerArea = "read-classrooms";

export default function ClassroomsComponent({ lng, pageData }: any) {

  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);
  const [dataAll, setDataAll] = useState<any>([]);
  const [search, setSearch] = useState<string>("");
  const [selectedDoc, setSelectedDoc] = useState<any>({});
  const [supervisors, setSupervisors] = useState<any>([]);

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
    label: t("classrooms.label_many"),
    url: "/admin/classrooms",
  }];

  const handleModalClose = () => setAddEditModalOpen(false);

  const handleModalOpen = () => setAddEditModalOpen(true);

  const onSearchTextChange = (event: any) => {
    setSearch(event.target.value);
    if (event.target.value === "") {
      setData(dataAll);
    }
  };

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
  const columns = ClassroomsCol(columnsPayload, t);

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <PageCardHeader
          onClick={onModalOpen}
          searchProps={false}
          showCreateModal={true}
          title={t("classrooms.label_many")}
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalClassrooms
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={data}
        setData={setData}
        classroom={selectedDoc}
        supervisors={supervisors}
        lng={lng}
      />
    </Layout>
  );
}
