"use client";

import React, { useEffect, useState } from "react";

// MUI
import { Box, Container } from "@mui/material";

// Components
import Breadcrumb from "@/app/ui/Breadcrumb";
import Layout from "@/app/ui/layout";
import PageCardHeader from "@/app/ui/PageCardHeader";
import SpinnerWithPromise from "@/app/ui/Spinner/SpinnerPromise";
import { DataGridComponent } from "@/app/ui/DataGrid";

// DB Operations
import { deleteCourse as deleteDoc, readExaminationPeriod, updateCourse as updateDoc, } from "@/app/lib/dbOperations";

// Helpers
import { useTranslation } from "@/app/i18n/client";
import SelectComponent from "@/app/ui/Form/SelectComponent";
import CreateEditModalExaminationPeriod from "@/app/ui/CRUDCreateEditModals/ExaminationPeriod";
import ExaminationPeriodsCol from "@/app/ui/CRUDColumns/Admin/ExaminationPeriods/Cols";

const entityNamePlural = "Courses";
const entityNameSingular = "Course";
const searchSpinnerArea = "search-courses-area";
const spinnerArea = "read-courses";

export default function ExaminationPeriodComponent({ lng, pageData }: any) {

  const { t } = useTranslation(lng);

  const [addEditModalOpen, setAddEditModalOpen] = useState(false);
  const [data, setData] = useState<any>([]);
  const [yearForSelect, setYearForSelect] = useState<any>({});
  const [selectedYear, setSelectedYear] = useState<any>("");

  useEffect(() => {
    const formattedYears = pageData?.map((year) => ({
      label: year.name,
      value: year.id
    }))
      .sort((a, b) => b.label.localeCompare(a.label));

    setYearForSelect(formattedYears);
  }, [pageData]);

  useEffect(() => {
    if (selectedYear) {
      (async () => {
        try {
          const examPeriod = await readExaminationPeriod(selectedYear);
          setData(examPeriod);
        } catch (error) {
          console.error("Error fetching examination period:", error);
        }
      })();
    }
  }, [selectedYear]);

  const breadcrumbList = [{
    label: "Home",
    url: "/",
  }, {
    label: "Admin",
    url: "/admin",
  }, {
    label: t("examination.label_many"),
    url: "/admin/examination-period",
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
  const columns = ExaminationPeriodsCol(columnsPayload, t);

  const onYearChange = (e: any) => {
    setSelectedYear(e.target.value);
  };

  const renderTitle = () => {
    return (
      <Box sx={{ display: "flex", gap: 4 }}>
        <Box>{t("examination.label_many")}</Box>
        <SelectComponent
          id="yearForSelect"
          placeHolder={"Select Academic Year"}
          onSelect={(e: any) => onYearChange(e)}
          value={selectedYear}
          size="small"
          menuItems={yearForSelect}
        />
      </Box>
    );
  };
  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Breadcrumb links={breadcrumbList}/>
        <PageCardHeader
          onClick={onModalOpen}
          searchProps={false}
          showCreateModal={true}
          title={renderTitle()}
          elementId="Examination Period"
        />
        <SpinnerWithPromise area="user-actions"/>
        <DataGridComponent
          columns={columns}
          rows={data}
          spinnerArea={spinnerArea}
        />
      </Container>
      <CreateEditModalExaminationPeriod
        open={addEditModalOpen}
        handleClose={handleModalClose}
        data={pageData}
        setData={setData}
        lng={lng}
      />
    </Layout>
  );
}
