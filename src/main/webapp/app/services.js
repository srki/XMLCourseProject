/**
 * Created by Srđan Milaković on 17.5.16..
 */

/*global angular*/
angular.module('app.services', [
    'app.Auth',
    'app.Users',
    'app.ForbiddenResponseInterceptor',
    'app.ContentTypeInterceptor',
    'app.AssemblyMeeting',
    'app.Acts'
]);
