import React, {Component} from 'react';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
 
export default class Product extends Component{

  render(){
    return (
      <TableRow>
        <TableCell>{this.props.product.id}</TableCell>
        <TableCell>{this.props.product.desc}</TableCell>
        <TableCell> {this.props.product.price}</TableCell>
      </TableRow>
    )
    
  }
    
}