import React, { useRef, useState } from "react";

// MUI
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import {
  Button,
  ButtonGroup,
  ClickAwayListener,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grow,
  MenuItem,
  MenuList,
  Paper,
  Popper,
} from "@mui/material";

export interface SplitButtonOptionsRef {
  label: string,
  value: string,
  disabled?: boolean
}

export interface WarningProps {
  open: boolean,
  title: string,
  message: string,
  agreeText?: string,
  disagreeText?: string,
  handleWarning: Function
}

interface SplitButtonProps {
  id: string,
  options: SplitButtonOptionsRef[],
  onClickSubmit: (option: SplitButtonOptionsRef) => Promise<any>,
  styles?: any
  warning?: WarningProps
}

export default function SplitButton({ id, options, onClickSubmit, warning, styles = {} }: SplitButtonProps) {
  const [open, setOpen] = useState(false);
  const anchorRef = useRef<HTMLDivElement>(null);
  const [selectedIndex, setSelectedIndex] = useState(0);

  const handleClick = async () => {
    if (warning?.handleWarning) {
      warning.handleWarning(true);
      return;
    }
    await onClickSubmit(options[selectedIndex]);
    warning?.handleWarning(false);
  };

  const handleMenuItemClick = (
      event: React.MouseEvent<HTMLLIElement, MouseEvent>,
      index: number,
  ) => {
    setSelectedIndex(index);
    setOpen(false);
  };

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event: Event) => {
    if (
      anchorRef.current &&
      anchorRef.current.contains(event.target as HTMLElement)
    ) {
      return;
    }

    setOpen(false);
  };

  const handleCloseDialogue = (agreed: boolean) => async () => {
    if (agreed && onClickAgree !== undefined) await onClickAgree(agreed);
    if (!agreed && warning?.handleWarning !== undefined) await warning.handleWarning(agreed);
    setOpen(false);
  };

  const onClickAgree = async (agreed: boolean) => {
    await onClickSubmit(options[selectedIndex]);
    if (warning?.handleWarning !== undefined) await warning.handleWarning(false);
  };

  return (
    <div style={styles}>
      <ButtonGroup variant="contained" ref={anchorRef} aria-label="split button" size="medium">
        <Button
          id={`emit-split-button-${id}`}
          onClick={handleClick}
          // size="large"
        >
          {options[selectedIndex]?.label}
        </Button>
        <Button
          aria-controls={open ? "split-button-menu" : undefined}
          aria-expanded={open ? "true" : undefined}
          aria-haspopup="menu"
          aria-label="select merge strategy"
          id={`open-split-button-${id}`}
          onClick={handleToggle}
          // size="large"
        >
          <ArrowDropDownIcon/>
        </Button>
      </ButtonGroup>
      <Popper
        anchorEl={anchorRef.current}
        disablePortal
        open={open}
        placement="auto"
        role={undefined}
        sx={{ zIndex: 1 }}
        transition
      >
        {({ TransitionProps, placement }) => (
          <Grow
            {...TransitionProps}
            style={{
              transformOrigin:
                placement === "bottom" ? "center top" : "center bottom",
            }}
          >
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList id={`split-button-menu-${id}`} autoFocusItem>
                  {options.map(({ value, label, disabled }, index: number) => (
                    <MenuItem
                      key={value}
                      disabled={disabled}
                      selected={index === selectedIndex}
                      onClick={(event) => handleMenuItemClick(event, index)}
                    >
                      {label}
                    </MenuItem>
                  ))}
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
      {warning && <Dialog
        open={warning.open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {warning.title}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {warning.message}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialogue(false)}>{warning.disagreeText || "Disagree"}</Button>
          <Button onClick={handleCloseDialogue(true)} autoFocus>
            {warning.agreeText || "Agree"}
          </Button>
        </DialogActions>
      </Dialog>}
    </div>
  );
}
