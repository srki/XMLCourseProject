/**
 * Created by Srđan on 14.6.2016..
 */
module.exports = function (sequelize, DataTypes) {
    return sequelize.define('act', {
        uri: {
            type: DataTypes.TEXT,
            primaryKey: true
        },
        xml: {
            type: DataTypes.STRING,
            allowNull: false
        }
    }, {
        tableName: 'act'
    });
};