// MUI
import { BtnDelete, BtnEdit, BtnSwitch } from "@/app/ui/Common/components";
import Box from "@mui/material/Box";
import { GridColDef } from "@mui/x-data-grid-pro";

export default function RolesCol(payload: any, t: any) {
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
        <BtnSwitch
          row={row}
          data={data}
          apiFunc={updateDoc}
          frontendFunc={setData}
          entityNameSingular={entityNameSingular}
          id={renderSwitchId(row)}
        />
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

  const columns: GridColDef[] = [
    {
      field: "order",
      headerName: t("roles.field.order"),
      width: 100,
    },
    {
      field: "name",
      headerName: t("roles.field.name"),
      width: 200,
    },
    {
      field: "permissions",
      headerName: t("roles.field.permissions"),
      width: 500,
      valueGetter: ({ row }: { row: any }) => row?.permissions?.join(", "),
    },
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
