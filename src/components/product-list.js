import React, {Component} from 'react';
import Product from './product';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import MyMenu from './menu';
import Paper from '@material-ui/core/Paper';
import useStyles from "./styles.js";
import { withStyles } from "@material-ui/core/styles";

class ProductList extends Component {
  state = {
    products: [
     {id:"0", desc:"iPad", price:20000},
     {id:"1", desc:"iPhone X", price:30000}
    ]
   } 

 render() {
  const { classes } = this.props;
  return (
  <div>
  <MyMenu/>
  <Paper className={classes.main}>
    
    <Table className={classes.table}>
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Desc</TableCell>
          <TableCell>Price</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
      { this.state.products.map((product, index) => <Product key={index} product={product}/>)}
      </TableBody> 
    </Table>
  </Paper>
  </div>
  )
 }

}
export default withStyles(useStyles) (ProductList); 