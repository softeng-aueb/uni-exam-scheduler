// MUI
import Box from "@mui/material/Box";

// Components
import { BtnDelete, BtnEdit } from "../../../Common/components";

export default function SupervisorsCol(payload: any, t: any) {
  const {
    data,
    deleteDoc,
    entityNameSingular,
    handleModalOpen,
    setData,
    setSelectedDoc,
    updateDoc,
  } = payload;

  //const renderSwitchId = (row: any) => {
  //  let id = "";
  //  if (row.name) {
  //    id = row.name.replaceAll(" ", "-");
  //  }
  //  return id;
  //};

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
      headerName: t("supervisors.field.id"),
      width: 100,
    },
    {
      field: "sname",
      flex: 1,
      headerName: t("supervisors.field.name"),
      renderCell: ({ row }: { row: any }) => `${row.name} ${row.surname}`
    },
    {
      field: "department",
      flex: 1,
      headerName: t("courses.field.department"),
      renderCell: ({ row }: { row: any }) => row?.department?.name
    },
    {
      field: "supervisor",
      flex: 1,
      headerName: t("courses.field.supervisor"),
      renderCell: ({ row }: { row: any }) => row?.supervisor
    },

    {
      field: "email",
      flex: 1,
      headerName: t("supervisors.field.email"),
    },
    {
      field: "supervisorCategory",
      flex: 1,
      headerName: t("supervisors.field.supervisor_category"),
    },
    {
      field: "telephone",
      flex: 1,
      headerName: t("supervisors.field.telephone"),
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
