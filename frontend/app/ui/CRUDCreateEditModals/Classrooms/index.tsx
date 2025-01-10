import * as React from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";

// Components
import { TextInput } from "../../Form/InputComponent";
import CreateEditModalCommon from "../Common/ModalStyles";


// Services
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { createClassroom, updateClassroom } from "@/app/lib/dbOperations/collections/Classrooms";
import { onAction } from "../../Common/cruds";
import { useTranslation } from "@/app/i18n/client";
import { Button } from "@mui/material";

export default function CreateEditModalClassrooms(props: any) {
  const { open, handleClose, classroom, supervisors, data, setData, lng } = props;
  const { t } = useTranslation(lng);

  const initialValues = {
    name: classroom?.name || "",
    building: classroom?.building || "",
    floor: classroom?.floor || "",
    covidCapacity: classroom?.covidCapacity || "",
    examCapacity: classroom?.examCapacity || "",
    generalCapacity: classroom?.generalCapacity || "",
    maxNumSupervisors: classroom?.maxNumSupervisors || "",
  };

  const validationSchema = yup.object({
    name: yup.string().trim().required("Name is required"),
    building: yup.string().trim().required("Building is required"),
    floor: yup
      .number()
      .integer("Floor must be an integer")
      .min(0, "Floor must be 0 or a positive integer")
      .required("Floor is required"),
    covidCapacity: yup
      .number()
      .integer("Covid Capacity must be an integer")
      .min(0, "Covid Capacity must be 0 or a positive integer")
      .required("Covid Capacity is required"),
    examCapacity: yup
      .number()
      .integer("Exam Capacity must be an integer")
      .min(0, "Exam Capacity must be 0 or a positive integer")
      .required("Exam Capacity is required"),
    generalCapacity: yup
      .number()
      .integer("General Capacity must be an integer")
      .min(0, "General Capacity must be 0 or a positive integer")
      .required("General Capacity is required"),
    maxNumSupervisors: yup
      .number()
      .integer("Max Number of Supervisors must be an integer")
      .min(0, "Max Number of Supervisors must be 0 or a positive integer")
      .required("Max Number of Supervisors is required"),
  });


  const spinnerArea = classroom?.id ? "update-classroom" : "create-classroom";
  const headerTitle = classroom?.id ? "Update Classroom" : "Create Classroom";

  const handleContinue = async (values: any) => {
    const payload = {
      frontendFunc: setData,
      data,
    };
    try {
      const resp = classroom?.id ? await updateClassroom(classroom.id, values) : await createClassroom(values);
      if (resp?.id) {
        values.id = resp?.id;
      }
      onAction(values, payload);
      handleClose();
    } catch (e: any) {
      console.log(e);
    }
  };

  const handleSupervisorChange = (e: any, setFieldValue: any) => {
    const val = e.target.value; // val is of the form `${el._id}---${el.name}`
    const id = val.split("---")[0];
    const name = val.split("---")[1];
    // setFieldValue("supervisor_id", Array.isArray(val) ? val.sort() : val);
    setFieldValue("supervisor_id", id);
    setFieldValue("supervisor_name", name);
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
                    placeHolder={t("classrooms.field.name")}
                    value={values.name}
                    handleTextChange={handleChange("name")}
                    hasError={!!errors.name}
                    errorMessage={errors.name as string}
                    id="name"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("classrooms.field.building")}
                    value={values.building}
                    handleTextChange={handleChange("building")}
                    hasError={!!errors.building}
                    errorMessage={errors.building as string}
                    id="building"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("classrooms.field.floor")}
                    value={values.floor}
                    handleTextChange={handleChange("floor")}
                    hasError={!!errors.floor}
                    errorMessage={errors.floor as string}
                    id="floor"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("classrooms.field.max_num_supervisors")}
                    value={values.maxNumSupervisors}
                    handleTextChange={handleChange("maxNumSupervisors")}
                    hasError={!!errors.maxNumSupervisors}
                    errorMessage={errors.maxNumSupervisors as string}
                    id="maxNumSupervisors"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("classrooms.field.covid_capacity")}
                    value={values.covidCapacity}
                    handleTextChange={handleChange("covidCapacity")}
                    hasError={!!errors.covidCapacity}
                    errorMessage={errors.covidCapacity as string}
                    id="covidCapacity"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextInput
                    placeHolder={t("classrooms.field.exam_capacity")}
                    value={values.examCapacity}
                    handleTextChange={handleChange("examCapacity")}
                    hasError={!!errors.examCapacity}
                    errorMessage={errors.examCapacity as string}
                    id="examCapacity"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("classrooms.field.general_capacity")}
                    value={values.generalCapacity}
                    handleTextChange={handleChange("generalCapacity")}
                    hasError={!!errors.generalCapacity}
                    errorMessage={errors.generalCapacity as string}
                    id="generalCapacity"
                  />
                </Grid>

                {/*<Grid item xs={12} md={12}>
                  <CheckboxComponent
                    checked={values.active}
                    handleChange={(e: any) => setFieldValue("active", e.target.checked)}
                    label="Active"
                    id="active_listing"
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
                    {classroom?.id ? t("classrooms.update_btn") : t("classrooms.create_btn")}
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
