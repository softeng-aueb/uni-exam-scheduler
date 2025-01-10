// MUI
import Box from "@mui/material/Box";

// Components
import { BtnDelete, BtnEdit } from "../../../Common/components";

export default function ClassroomsCol(payload: any, t: any) {
  const {
    data,
    deleteDoc,
    entityNameSingular,
    handleModalOpen,
    setData,
    setSelectedDoc,
    updateDoc,
  } = payload;

  const renderSwitchId = (row: any) => {
    let id = "";
    if (row.name) {
      id = row.name.replaceAll(" ", "-");
    }
    return id;
  };

  const renderButtons = (row: any) => {
    return (
      <Box sx={{ display: "flex", gap: 1 }}>
        {/*<BtnSwitch
          row={row}
          data={data}
          apiFunc={updateDoc}
          frontendFunc={setData}
          entityNameSingular={entityNameSingular}
          id={renderSwitchId(row)}
        />*/}
        <BtnEdit
          row={row}
          setData={setSelectedDoc}
          setModalOpen={handleModalOpen}
        />
        <BtnDelete
          row={row}
          data={data}
          apiFunc={deleteDoc}
          frontendFunc={setData}
          entityNameSingular={entityNameSingular}
        />
      </Box>
    );
  };

  const columns: any[] = [
    {
      field: "id",
      headerName: t("classrooms.field.id"),
      width: 100,
    },
    {
      field: "name",
      flex: 1,
      headerName: t("classrooms.field.name"),
    },
    {
      field: "building",
      flex: 1,
      headerName: t("classrooms.field.building"),
    },
    {
      field: "floor",
      flex: 1,
      headerName: t("classrooms.field.floor"),
    },
    {
      field: "covidCapacity",
      flex: 1,
      headerName: t("classrooms.field.covid_capacity"),
    },
    {
      field: "examCapacity",
      flex: 1,
      headerName: t("classrooms.field.exam_capacity"),
    },
    {
      field: "generalCapacity",
      flex: 1,
      headerName: t("classrooms.field.general_capacity"),
    },
    {
      field: "maxNumSupervisors",
      flex: 1,
      headerName: t("classrooms.field.max_num_supervisors"),
    },
    //{
    //  field: "active",
    //  headerName: "Active",
    //  renderCell: ({ row }: { row: any }) => row?.active ? <CheckIcon/> : "",
    //  width: 100,
    //},
    {
      align: "center",
      disableColumnMenu: true,
      disableExport: true,
      field: "actions",
      headerAlign: "center",
      headerName: "",
      renderCell: ({ row }: { row: any }) => renderButtons(row),
      resizable: false,
      sortable: false,
      width: 175,
    },
  ];

  return columns;
}
