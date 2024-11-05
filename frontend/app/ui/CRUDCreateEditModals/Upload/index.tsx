import React, { useEffect, useState } from "react";
import { Form, Formik } from "formik";
import * as yup from "yup";

// MUI
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";
import FormLabel from "@mui/material/FormLabel";

// Components
import CreateEditModalCommon from "../Common/ModalStyles";

// Services
import { uploadFile } from "@/app/lib/dbOperations/collections/Upload";
import { readAcademicYears, readExaminationPeriod } from "@/app/lib/dbOperations";
import SelectComponent from "../../Form/SelectComponent";
import { infoAlert, warningAlert } from "@/app/lib/helpers/swalGenerator";

const Input = styled('input')({
  display: 'none',
});

const UPLOAD_CATEGORIES = [
  { label: "Course Declarations", value: "course_declarations" },
  { label: "Course Attendances", value: "course_attendances" },
  { label: "Supervisor Preferences", value: "supervisor_preferences" },
  { label: "Examinations", value: "examinations" },
];

export default function FileUploadModal(props: any) {
  const { open, handleClose, data, setData } = props;
  const [yearForSelect, setYearForSelect] = useState<any>([])
  const [selectedYear, setSelectedYear] = useState<any>("")
  const [examForSelect, setExamForSelect] = useState<any>([])

  useEffect(() => {
    (async () => {
      const academicYears = await readAcademicYears();
      const formattedYears = academicYears?.map((year) => ({
        label: year.name,
        value: year.id
      }))
        .sort((a, b) => b.label.localeCompare(a.label));

      setYearForSelect(formattedYears);
    })()
  }, [])

  const initialValues = {
    file: null,
    type: 'Academic Year',
    academicYear: "",
    examPeriod: "",
    uploadType: ""
  };

  const validationSchema = yup.object({
    file: yup.mixed().required("File is required"),
  });

  const headerTitle = "Upload File";

  const handleContinue = async (values: any) => {
    ;

    const formData = new FormData();
    formData.append("file", values.file);

    const payload = {
      academicYear: values.academicYear,
      uploadType: values.uploadType,
      examPeriod: values.examPeriod,
      type: values.type
    }

    try {
      const resp: any = await uploadFile(formData, payload);
      handleClose();
      infoAlert("Success", "File uploaded successfully!")
    } catch (e: any) {
      warningAlert("Something went wrong!")
      console.log(e);
    }
  };

  useEffect(() => {
    if (selectedYear) {
      (async () => {
        try {
          const examPeriod = await readExaminationPeriod(selectedYear);
          const formattedYears = examPeriod?.map((p) => ({
            label: p.period,
            value: p.id
          }))
            .sort((a, b) => b.label.localeCompare(a.label));
          setExamForSelect(formattedYears)
        } catch (error) {
          console.error("Error fetching examination period:", error);
        }
      })();
    }
  }, [selectedYear]);

  const handleChangeAcademicYear = (setFieldValue, val) => {
    setFieldValue("academicYear", val.target.value)
    setSelectedYear(val.target.value)
  }

  return (
    <CreateEditModalCommon open={open} handleClose={handleClose} headerTitle={headerTitle}>
      <Formik
        enableReinitialize={true}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={async (values: any) => await handleContinue(values)}
      >
        {({ values, handleSubmit, errors, setFieldValue }) => {
          return (
            <Form>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  {/* Radio Buttons Section */}
                  <Box sx={{ backgroundColor: '#E1E1E1', padding: 2 }}>
                    <FormControl component="fieldset">
                      <FormLabel component="legend">Choose Type</FormLabel>
                      <RadioGroup
                        row
                        name="type"
                        value={values.type}
                        onChange={(event) => setFieldValue('type', event.target.value)}
                      >
                        <FormControlLabel
                          value="Examination Period"
                          control={<Radio/>}
                          label="Examination Period"
                        />
                        <FormControlLabel
                          value="Academic Year"
                          control={<Radio/>}
                          label="Academic Year"
                        />
                      </RadioGroup>
                    </FormControl>
                  </Box>
                  {/*Academic Year Selection*/}
                  <Box sx={{ padding: 2, backgroundColor: "#E1E1E1", marginTop: 2 }}>
                    <Box>Choose Academic Year</Box>
                    <SelectComponent
                      id="yearForSelect"
                      placeHolder={"Select Academic Year"}
                      onSelect={(e: any) => handleChangeAcademicYear(setFieldValue, e)}
                      value={values.academicYear}
                      size="small"
                      menuItems={yearForSelect}
                    />
                  </Box>
                  {values.type === "Examination Period" &&
                    <Box sx={{ padding: 2, backgroundColor: "#E1E1E1", marginTop: 2 }}>
                      <Box>Choose Examination Period</Box>
                      <SelectComponent
                        id="examPeriodSelect"
                        placeHolder={"Select Examination Period"}
                        onSelect={(e: any) => setFieldValue("examPeriod", e.target.value)}
                        value={values.examPeriod}
                        size="small"
                        menuItems={examForSelect}
                      />
                    </Box>}
                  {/*Upload Types Selection*/}
                  <Box sx={{ padding: 2, backgroundColor: "#E1E1E1", marginTop: 2 }}>
                    <Box>Choose Upload Type</Box>
                    <SelectComponent
                      id="uploadType"
                      placeHolder={"Select Upload Type"}
                      onSelect={(e: any) => setFieldValue("uploadType", e.target.value)}
                      value={values.uploadType}
                      size="small"
                      menuItems={UPLOAD_CATEGORIES}
                    />
                  </Box>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">Choose a file to upload:</Typography>
                  <label htmlFor="file-upload">
                    <Input
                      id="file-upload"
                      type="file"
                      onChange={(event) => {
                        if (event.currentTarget.files && event.currentTarget.files[0]) {
                          setFieldValue("file", event.currentTarget.files[0]);
                        }
                      }}
                    />
                    <Box>{values?.file?.name}</Box>
                    <Button
                      variant="contained"
                      component="span"
                    >
                      Browse File
                    </Button>
                  </label>
                  {errors.file && typeof errors.file === 'string' && (
                    <div style={{ color: 'red' }}>{errors.file}</div>
                  )}
                </Grid>
              </Grid>
              <Grid container spacing={2} marginTop={2}>
                <Grid item xs={12} sm={6} md={6} lg={6}>
                  <Button
                    type="submit"
                    disabled={false}
                    variant="contained"
                  >
                    Upload
                  </Button>
                </Grid>
              </Grid>
            </Form>
          )
        }}
      </Formik>
    </CreateEditModalCommon>
  );
}
