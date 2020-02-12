import { makeStyles } from '@material-ui/core/styles';
const useStyles = makeStyles(theme => ({
    root: {
      //width: '85%',
      marginTop: theme.spacing(3),
      marginLeft: theme.spacing(3),
      marginRight: theme.spacing(3),
      overflowX: 'auto',
    },
    table: {
      minWidth: 650,
    },
    main: {
      paddingTop: theme.spacing(8)
    },
  }));

  export default useStyles;