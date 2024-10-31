
import Button from '@mui/material/Button';

type ButtonProps = {
    elementId: string;
    onClick? : any;
}

export default function CreateButton({elementId, onClick}: ButtonProps){
    return(
        <Button 
            id={`create-button-${elementId}`} 
            variant="contained"
            {...(onClick && { onClick })}
            >
            Create new
        </Button>

    )
}
