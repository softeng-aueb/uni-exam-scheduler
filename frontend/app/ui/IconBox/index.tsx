import React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';

type IconBoxProps = {
  icon: React.ReactNode;
};

const StyledBox = styled(Box)({
  backgroundColor: '#daeeef',
  borderRadius: '64px',
  height: '64px',
  minHeight: '64px',
  minWidth: '64px',
  position: 'relative',
  width: '64px',
});

const IconWrapper = styled(Box)({
  position: 'absolute',
  top: '34px',
  left: '32px',
  transform: 'translate(-50%, -50%)',
});

export default function IconBox({ icon }: IconBoxProps) {
  return (
    <StyledBox>
      <IconWrapper>
        {icon}
      </IconWrapper>
    </StyledBox>
  );
}
