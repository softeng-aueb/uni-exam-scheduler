import React, { useEffect, useState } from "react";
import {
  IconButton,
  MenuItem,
  Paper,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import AddIcon from "@mui/icons-material/Add";
import { addSupervisorToExam, deleteSupervisorToExam, readSupervisors } from "@/app/lib/dbOperations";
import SelectComponent from "@/app/ui/Form/SelectComponent";
import { successAlert, warningAlert } from "@/app/lib/helpers/swalGenerator";

const groupedData = (data) => {
    
    const sortedData = [...data].sort((a, b) => {
      const dateA = Date.parse(a.date);
      const dateB = Date.parse(b.date);
  
      if (!isNaN(dateA) && !isNaN(dateB)) {
        return dateB - dateA;
      }
      return 0;
    });
  
    // Group sorted data by date
    return sortedData.reduce((acc, curr) => {
      const date = curr.date;
      if (!acc[date]) acc[date] = [];
      acc[date].push(curr);
      return acc;
    }, {});
  };
  
const ExaminationTable = ({ data }) => {

  const [tableData, setTableData] = useState<any>([]);
  const [supervisors, setSupervisors] = useState<any>([]);
  const [supervisorsForSelect, setSupervisorsForSelect] = useState<any>([]);
  const [selectedSupervisor, setSelectedSupervisor] = useState("");

  useEffect(()=>{
    (async()=>{
        const supervisors = await readSupervisors();
        const formattedSupervisorList = [];
        supervisors.forEach((supervisor) => {
            formattedSupervisorList.push({label: supervisor.name, value: supervisor.id})
        });
        setSupervisors(supervisors)
        setSupervisorsForSelect(formattedSupervisorList)
    })()
    if(data){
        setTableData(groupedData(data))
    }
  },[data]);

  const handleChangeSupervisor = (supervisor) => {
    setSelectedSupervisor(supervisor);
   
  };

  const handleAddSupervisor = async(date, courseId) => {
   
    const sup = supervisors.find((s)=>s.id === selectedSupervisor);
    try{
        await addSupervisorToExam(courseId, sup);

        setTableData((prevData) => ({
          ...prevData,
          [date]: prevData[date].map((course) => {
            if (course.id === courseId) {
              return {
                ...course,
                supervisions: [...course.supervisions, {supervisor: sup}]
              };
            }
            return course;
          })
        }));
        successAlert("Success", "Supervisor added successfully!")
    }catch(e){
        warningAlert("Something went wrong!")
    }
  };

  const handleRemoveSupervisor = async(date, courseId, supervisorId) => {
try{
    const resp = await deleteSupervisorToExam(courseId,supervisorId)
    setTableData((prevData) => ({
        ...prevData,
        [date]: prevData[date].map((course) => {
          if (course.id === courseId) {
            return {
              ...course,
              supervisions: course.supervisions.filter(
                (sup) => sup.id !== supervisorId
              )
            };
          }
          return course;
        })
      }));
            successAlert("Success", "Supervisor removed successfully!")
        }catch(e){
            warningAlert("Something went wrong!")
        }
  };

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Date</TableCell>
            <TableCell>Course Name</TableCell>
            <TableCell>Course Code</TableCell>
            <TableCell>Start Time</TableCell>
            <TableCell>End Time</TableCell>
            <TableCell>Classrooms</TableCell>
            <TableCell>Declaration</TableCell>
            <TableCell>Estimated Attendance</TableCell>
            <TableCell>Max Supervisors</TableCell>
            <TableCell>Supervisors</TableCell>
          </TableRow>
        </TableHead>
       <TableBody>
  {Object.keys(tableData).map((date) => (
    tableData[date].map((course, index) => (
      <TableRow key={course.id}>
        {index === 0 && (
          <TableCell
            rowSpan={tableData[date].length}
            style={{
              backgroundColor: "#f2dede",
              writingMode: "vertical-rl",
              textAlign: "center",
            }}
          >
            <Typography variant="h6">{date}</Typography>
          </TableCell>
        )}
        <TableCell>{course.course.title}</TableCell>
        <TableCell>{course.course.courseCode}</TableCell>
        <TableCell>{course.startTime}</TableCell>
        <TableCell>{course.endTime}</TableCell>
        <TableCell>
          {course.classrooms.map((room) => room.name).join(", ")}
        </TableCell>
        <TableCell>{course.declaration}</TableCell>
        <TableCell>{course.estimatedAttendance}</TableCell>
        <TableCell>{course.maxSupervisors}</TableCell>
        <TableCell>
          {course.supervisions.map((supervision) => (
            <div key={supervision.id}>
              {supervision.supervisor.name} {supervision.supervisor.surname}
              <IconButton
                size="small"
                onClick={() =>
                  handleRemoveSupervisor(date, course.id, supervision.id)
                }
              >
                <DeleteIcon fontSize="small" />
              </IconButton>
            </div>
          ))}
          <SelectComponent
            menuItems={supervisorsForSelect}
            onSelect={(e)=>handleChangeSupervisor(e.target.value)}
            value={selectedSupervisor}
          />
          {/*<Select
            size="small"
            displayEmpty
            defaultValue=""
            onChange={(e) =>
              handleAddSupervisor(date, course.id, {
                id: new Date().getTime(),
                supervisor: { name: e.target.value, surname: "" },
              })
            }
          >
            <MenuItem value="" disabled>
              Select Supervisor
            </MenuItem>
            <MenuItem value="Supervisor A">Supervisor A</MenuItem>
            <MenuItem value="Supervisor B">Supervisor B</MenuItem>
          </Select>*/}
          <IconButton
            onClick={(e)=>handleAddSupervisor(date, course.id)}
          >
            <AddIcon fontSize="small" />
          </IconButton>
        </TableCell>
      </TableRow>
    ))
  ))}
</TableBody>

      </Table>
    </TableContainer>
  );
};

export default ExaminationTable;
