// MUI
import Box from "@mui/material/Box";

// Components
import { BtnDelete, BtnEdit } from "../../../Common/components";

export default function AcademicYearsCol(payload: any, t: any) {
  const {
    data,
    deleteDoc,
    entityNameSingular,
    handleModalOpen,
    setData,
    setSelectedDoc,
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
      field: "name",
      headerName: t("academic.field.name"),
      flex: 1,
    },
    //{
    //  align: "center",
    //  disableColumnMenu: true,
    //  disableExport: true,
    //  field: "actions",
    //  headerAlign: "center",
    //  headerName: "",
    //  renderCell: ({ row }: { row: any }) => renderButtons(row),
    //  resizable: false,
    //  sortable: false,
    //  width: 175,
    //},
  ];

  return columns;
}
