// MUI
import Box from "@mui/material/Box";
import CheckIcon from "@mui/icons-material/Check";

// Components
import { BtnDelete, BtnEdit, BtnSwitch } from "../../../Common/components";

export default function CountryCol(payload: any, t: any) {
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

  const columns: any[] = [
    {
      field: "order",
      headerName: t("countries.field.order"),
      width: 100,
    },
    {
      field: "name",
      flex: 1,
      headerName: t("countries.field.name"),
    },
    {
      field: "iso2",
      flex: 1,
      headerName: t("countries.field.iso2"),
    },
    {
      field: "iso3",
      flex: 1,
      headerName: t("countries.field.iso3"),
    },
    {
      field: "active",
      headerName: "Active",
      renderCell: ({ row }: { row: any }) => row?.active ? <CheckIcon/> : "",
      width: 100,
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
