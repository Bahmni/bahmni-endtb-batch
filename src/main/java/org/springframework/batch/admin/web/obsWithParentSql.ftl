SELECT obs0.obs_id,obs${form.depthToParent}.obs_id as parent_obs_id
FROM obs obs0
<#if form.depthToParent &gt; 0>
<#list 1..form.depthToParent as x>
INNER JOIN obs obs${x} on ( obs${x}.obs_id=obs${x-1}.obs_group_id and obs${x}.voided=0 )
</#list>
</#if>
WHERE obs0.concept_id=${form.formName.id?c}
AND obs0.voided = 0
<#if form.parent?has_content>
AND obs${form.depthToParent}.concept_id=${form.parent.formName.id?c}
</#if>
