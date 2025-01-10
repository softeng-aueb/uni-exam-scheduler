import * as React from "react";

// MUI
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import Popper from "@mui/material/Popper";
import { GridRenderCellParams } from "@mui/x-data-grid-pro";

// Component
import TextFormatter from "./TextFormatter";

// Interface
import { IGridCellExpandProps } from "../../lib/dbOperations/schemas/ComponentsInterfaces";

function isOverflown(element: Element): boolean {
  return (
    element.scrollHeight > element.clientHeight ||
    element.scrollWidth > element.clientWidth
  );
}

const getProps = (props: IGridCellExpandProps): IGridCellExpandProps => {
  if (props.shouldUsePre) return props;

  const { value } = props;
  return { ...props, width: Math.round(value.length * 10.2) };
};

const GridCellExpand = React.memo(function GridCellExpand(
  props: IGridCellExpandProps,
) {
  const { width, value, shouldUsePre } = getProps(props);

  const wrapper = React.useRef<HTMLDivElement | null>(null);
  const cellDiv = React.useRef(null);
  const cellValue = React.useRef(null);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [showFullCell, setShowFullCell] = React.useState(false);
  const [showPopper, setShowPopper] = React.useState(false);

  const handleMouseEnter = () => {
    const isCurrentlyOverflown = isOverflown(cellValue.current!);
    setShowPopper(isCurrentlyOverflown);
    setAnchorEl(cellDiv.current);
    setShowFullCell(true);
  };

  const handleMouseLeave = () => {
    setShowFullCell(false);
  };

  React.useEffect(() => {
    if (!showFullCell) {
      return undefined;
    }

    function handleKeyDown(nativeEvent: KeyboardEvent) {
      // IE11, Edge (prior to using Bink?) use 'Esc'
      if (nativeEvent.key === "Escape" || nativeEvent.key === "Esc") {
        setShowFullCell(false);
      }
    }

    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, [setShowFullCell, showFullCell]);

  return (
    <Box
      ref={wrapper}
      color="GrayText"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      sx={{
        alignItems: "center",
        lineHeight: "24px",
        width: 1,
        height: 1,
        position: "relative",
        display: "flex",
      }}
    >
      <Box
        ref={cellDiv}
        sx={{
          height: 1,
          width,
          display: "block",
          position: "absolute",
          top: 0,
        }}
      />
      <Box
        ref={cellValue}
        sx={{ whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis" }}
      >
        {value}
      </Box>
      {showPopper && (
        <Popper
          open={showFullCell && anchorEl !== null}
          anchorEl={anchorEl}
          style={{ width, marginLeft: -17 }} // ,
        >
          <Paper
            elevation={1}
            style={{ minHeight: wrapper.current!.offsetHeight - 3, backgroundColor: "gray", color: "white" }}
          >
            <Typography variant="body2" style={{ padding: 8 }}>
              {shouldUsePre ? <TextFormatter text={value}/> : value}
            </Typography>
          </Paper>
        </Popper>
      )}
    </Box>
  );
});

export default function renderCellExpand(params: GridRenderCellParams<any>, shouldUsePre = false) {
  return (
    <GridCellExpand
      value={params.value || ""}
      width={params.colDef?.computedWidth}
      shouldUsePre={shouldUsePre}
    />
  );
}
