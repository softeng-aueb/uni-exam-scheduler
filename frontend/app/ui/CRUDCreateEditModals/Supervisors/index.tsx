import React, { useEffect, useState } from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";

// Components
import { TextInput } from "../../Form/InputComponent";
import CreateEditModalCommon from "../Common/ModalStyles";

// Services
import { createSupervisor, updateSupervisor } from "@/app/lib/dbOperations/collections/Supervisors";
import { onAction } from "../../Common/cruds";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";
import SelectComponent from "../../Form/SelectComponent";
import { readDepartments } from "@/app/lib/dbOperations";
import { Button } from "@mui/material";

const SUPERVISOR_CATEGORIES = [
  { label: "EDIP", value: "EDIP" },
  { label: "ETEP", value: "ETEP" },
  { label: "PHD", value: "PHD" },
  { label: "DISPATCHED", value: "DISPATCHED" },
  { label: "EXTERNAL", value: "EXTERNAL" },
]
export default function CreateEditModalSupervisors(props: any) {
  const { open, handleClose, supervisor, data, setData, lng } = props;
  const [departments, setDepartments] = useState([]);
  const [departmentsForSelect, setDepartmentsForSelect] = useState([]);

  const { t } = useTranslation(lng);

  useEffect(() => {
    (async () => {
      const formattedDepartments = [];
      const depts: any = await readDepartments();
      if (depts.length) {
        depts.forEach((department) => {
          formattedDepartments.push({ label: department.name, value: department.id })
        })
      }
      setDepartments(depts);
      setDepartmentsForSelect(formattedDepartments)
    })()
  }, []);

  const initialValues = {
    name: supervisor?.name || "",
    surname: supervisor?.surname || "",
    supervisor: supervisor?.supervisor || "",
    department: supervisor?.department?.id || "",
    supervisorCategory: supervisor?.supervisorCategory || "",
    telephone: supervisor?.telephone || "",
    email: supervisor?.email || "",
    /* */
  };

  const validationSchema = yup.object({
    name: yup.string().trim().required("name is required"),
    surname: yup.string().trim().required("surname is required"),
    supervisor: yup.string().trim().required("supervisor is required"),
    department: yup.string().trim().required("department is required"),
    supervisorCategory: yup.string().trim().required("supervisorCategory is required"),
    telephone: yup.string().trim().required("telephone is required"),
    email: yup.string().trim().required("email is required")
  });

  const spinnerArea = supervisor?.id ? "update-supervisor" : "create-supervisor";
  const headerTitle = supervisor?.id ? "Update Supervisor" : "Create Supervisor";

  const handleContinue = async (values: any) => {
    const dept = departments.find((d) => d.id === values.department);
    values.department = dept;

    const payload = {
      frontendFunc: setData,
      data,
    };
    try {
      const resp = supervisor?.id ? await updateSupervisor(supervisor.id, values) : await createSupervisor(values);
      if (resp?.id) {
        values.id = resp?.id;
      }
      onAction(values, payload);
      handleClose();
    } catch (e: any) {
      console.log(e);
    }
  };
  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={headerTitle}>
      <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values: any) => await handleContinue(values)}
      >
        {({ values, handleSubmit, errors, setFieldValue, handleChange, dirty }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("supervisors.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("supervisors.field.surname")}
                    value={values.surname}
                    handleTextChange={handleChange("surname")}
                    hasError={!!errors.surname}
                    errorMessage={errors.surname as string}
                    id="surname"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("supervisors.field.supervisor")}
                    value={values.supervisor}
                    handleTextChange={handleChange("supervisor")}
                    hasError={!!errors.supervisor}
                    errorMessage={errors.supervisor as string}
                    id="supervisor"
                  />
                </Grid>
                <Grid item xs={12}>
                  <SelectComponent
                    menuItems={departmentsForSelect}
                    value={values.department}
                    onSelect={handleChange("department")}
                    placeHolder="Department"
                  />
                </Grid>
                <Grid item xs={12}>
                  <SelectComponent
                    menuItems={SUPERVISOR_CATEGORIES}
                    value={values.supervisorCategory}
                    onSelect={handleChange("supervisorCategory")}
                    placeHolder={t("supervisors.field.supervisor_category")}
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("supervisors.field.email")}
                    value={values.email}
                    handleTextChange={handleChange("email")}
                    hasError={!!errors.email}
                    errorMessage={errors.email as string}
                    id="email"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("supervisors.field.telephone")}
                    value={values.telephone}
                    handleTextChange={handleChange("telephone")}
                    hasError={!!errors.telephone}
                    errorMessage={errors.telephone as string}
                    id="telephone"
                  />
                </Grid>
                {/*<Grid item xs={12} md={12}>
                  <CheckboxComponent
                    checked={values.active}
                    handleChange={(e: any) => setFieldValue("active", e.target.checked)}
                    label="Active"
                    id="active_supervisor"
                  />
                </Grid>*/}
              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <Button
                    type="submit"
                    onClick={() => handleSubmit()}
                    disabled={false}
                    variant="contained"
                  >
                    {supervisor?.id ? t("supervisors.update_btn") : t("supervisors.create_btn")}
                  </Button>
                </Grid>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <SpinnerWithPromise area={spinnerArea}/>
                </Grid>
              </Grid>
            </Form>
          );
        }}
      </Formik>
    </CreateEditModalCommon>

  );
}
