// MUI
import Box from "@mui/material/Box";
import CheckIcon from "@mui/icons-material/Check";
import Chip from "@mui/material/Chip";
import { GridColDef } from "@mui/x-data-grid-pro";
import { BtnDelete, BtnEdit, BtnSwitch } from "@/app/ui/Common/components";

export default function UsersCol(payload: any, t: any) {
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
    if (row.username) {
      id = row.username.replaceAll(" ", "-");
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
      field: "username",
      headerName: "Username",
      width: 200,
    },
    {
      field: "email",
      headerName: "Email Address",
      width: 200,
    },
    {
      field: "is_admin",
      headerName: "Admin",
      width: 75,
      renderCell: ({ row }: { row: any }) => row?.is_admin ? <CheckIcon/> : "",
    },
    {
      field: "roles",
      headerName: "UserRoles",
      width: 500,
      renderCell: ({ row }: { row: any }) => {
        return row?.roles.map((t) => {
          return <Chip sx={{ margin: "4px 4px 4px 0" }} key={t} label={t} variant="outlined" size="small"/>;
        });
      },
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
