import React, { useCallback, useEffect, useState } from "react";
import { Button, Card, CardContent, Container, Box, Modal, Typography } from "@mui/material";
import Layout from "@/app/ui/layout";
import { deleteSupervision, readAcademicYears, readExaminationPeriod, readExaminationPeriodById, solveExaminationPeriod } from "@/app/lib/dbOperations";
import SelectComponent from "@/app/ui/Form/SelectComponent";
import ExaminationTable from "./ExaminationTable"; 
import { successAlert, warningAlert } from "@/app/lib/helpers/swalGenerator";

export default function CalendarEventsComponent({ lng }: any) {
  const [loading, setLoading] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedYear, setSelectedYear] = useState("");
  const [yearForSelect, setYearForSelect] = useState<any>([]);
  const [examForSelect, setExamForSelect] = useState<any>([]);
  const [examPeriod, setExamPeriod] = useState<any>("");
  const [tableData, setTableData] = useState<any>([]); 

  useEffect(() => {
    async function init() {
      setLoading(true);
      const academicYears = await readAcademicYears();
      const formattedYears = academicYears
        ?.map((year) => ({ label: year.name, value: year.id }))
        .sort((a, b) => b.label.localeCompare(a.label));

      setYearForSelect(formattedYears);
      setLoading(false);
    }

    init();
  }, []);

  useEffect(() => {
    if (selectedYear) {
      (async () => {
        try {
          const examPeriod = await readExaminationPeriod(selectedYear);
          const formattedYears = examPeriod
            ?.map((p) => ({ label: p.period, value: p.id }))
            .sort((a, b) => b.label.localeCompare(a.label));
          setExamForSelect(formattedYears);

        } catch (error) {
          console.error("Error fetching examination period:", error);
        }
      })();
    }
  }, [selectedYear]);

  useEffect(() => {
    if (examPeriod) {
      (async () => {
        try {
          const examPeriods = await readExaminationPeriodById(examPeriod);
          setTableData(examPeriods)
        } catch (error) {
          console.error("Error fetching examination period:", error);
        }
      })();
    }
  }, [examPeriod]);

  const handleSolve = useCallback(async()=>{
        setModalOpen(true);
        setTimeout(() => {
        setModalOpen(false);
        }, 30000); // 30 sec
    try {
      const resp = await solveExaminationPeriod(examPeriod);
      console.log("++ :", resp)
      setTableData(resp)
    } catch (error) {
      console.error("Error solving examination period:", error);
      setModalOpen(false);
    }
  },[tableData])

  const handleClearSupervisor = async () => {
   if(examPeriod){
    try{
      const resp = await deleteSupervision(examPeriod);
      successAlert("Success", "Supervision deleted successfully!")
    }catch(e){
      warningAlert("Something went wrong!")
    }
   }
  };

  return (
    <Layout>
      <Container disableGutters maxWidth="xl">
        <Card sx={{ width: "100%", marginBottom: 4 }} variant="outlined">
          <CardContent>
            <Box display="flex" justifyContent="space-between" alignItems="center">
              <Box display="flex" gap={2}>
                <SelectComponent
                  id="yearForSelect"
                  placeHolder={"Select Academic Year"}
                  onSelect={(e: any) => setSelectedYear(e.target.value)}
                  value={selectedYear}
                  containerStyle={{ width: 200 }}
                  menuItems={yearForSelect}
                />
                <SelectComponent
                  id="examPeriod"
                  placeHolder={"Select Exam Period"}
                  onSelect={(e: any) => setExamPeriod(e.target.value)}
                  value={examPeriod}
                  containerStyle={{ width: 200 }}
                  menuItems={examForSelect}
                />
                <Button variant="contained" onClick={handleSolve}>
                  Solve
                </Button>
              </Box>
              <Button variant="contained" color="error" onClick={handleClearSupervisor}>
                Clear Supervisions
              </Button>
            </Box>
          </CardContent>
        </Card>

        {/* Examination Table */}
        <Card sx={{ width: "100%" }} variant="outlined">
          <CardContent>
            <ExaminationTable data={tableData} />
          </CardContent>
        </Card>

        {/* Modal */}
        <Modal
          open={modalOpen}
          onClose={() => {}}
          BackdropProps={{ onClick: () => {} }} 
        >
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              justifyContent: "center",
              position: "absolute",
              top: "50%",
              left: "50%",
              transform: "translate(-50%, -50%)",
              bgcolor: "background.paper",
              borderRadius: 1,
              boxShadow: 24,
              p: 4,
            }}
          >
            <Typography variant="h6" component="h2">
              SCHEDULING... Please Wait
            </Typography>
          </Box>
        </Modal>
      </Container>
    </Layout>
  );
}
