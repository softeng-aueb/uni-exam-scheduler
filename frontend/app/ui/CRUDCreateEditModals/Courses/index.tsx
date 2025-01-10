import React, { useEffect, useState } from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";

// Components
import { TextInput } from "../../Form/InputComponent";
import CreateEditModalCommon from "../Common/ModalStyles";

// Services
import { createCourse, updateCourse } from "@/app/lib/dbOperations/collections/Courses";
import { onAction } from "../../Common/cruds";
import SpinnerWithPromise from "../../Spinner/SpinnerPromise";
import { useTranslation } from "@/app/i18n/client";
import { readDepartments } from "@/app/lib/dbOperations";
import SelectComponent from "../../Form/SelectComponent";

export default function CreateEditModalCourses(props: any) {
  const { open, handleClose, course, data, setData, lng } = props;
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
    name: course?.courseCode || "",
    department: course?.department?.id || null,
    title: course?.title || ""
  };

  const validationSchema = yup.object({
    courseCode: yup.string().trim().required("Course Code is required"),
    department: yup.string().trim().required("Department is required"),
    title: yup.string().trim().required("Course Title is required"),
  });

  const spinnerArea = course?.id ? "update-course" : "create-course";
  const headerTitle = course?.id ? "Update Course" : "Create Course";

  const handleContinue = async (values: any) => {
    const dept = departments.find((d) => d.id === values.department);
    values.department = dept;
    const payload = {
      frontendFunc: setData,
      data,
    };
    try {
      const resp = course?.id ? await updateCourse(course.id, values) : await createCourse(values);
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
                <Grid item xs={12}>
                  <TextInput
                    placeHolder={t("courses.field.code")}
                    value={values.courseCode}
                    handleTextChange={handleChange("courseCode")}
                    hasError={!!errors.courseCode}
                    errorMessage={errors.courseCode as string}
                    id="courseCode"
                  />
                </Grid>
                <Grid item xs={12}>
                  <SelectComponent
                    menuItems={departmentsForSelect}
                    value={values.department}
                    onSelect={handleChange("department")}
                    placeHolder="Department"
                  />
                  <TextInput
                    placeHolder={t("courses.field.title")}
                    value={values.title}
                    handleTextChange={handleChange("title")}
                    hasError={!!errors.title}
                    errorMessage={errors.title as string}
                    id="title"
                  />
                </Grid>

              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <Button
                    type="submit"
                    onClick={() => handleSubmit()}
                    disabled={false}
                    variant="contained"
                  >
                    {course?.id ? t("courses.update_btn") : t("courses.create_btn")}
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
